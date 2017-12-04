package net.eoutech.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class CosTbRow 
{
	private String tbName;
	private Map<String,Object> rowdata= new HashMap<String,Object>();
	
	public CosTbRow(String tbname)
	{
		this.tbName = tbname;
	}
	
	public void putData(Object...args)
	{		
		if (args.length==1 && args[0] instanceof Map<?,?>) {
			@SuppressWarnings("unchecked")
			Map<String,Object> map = (Map<String,Object>)args[0];
			rowdata.putAll(map);
		}else if (args.length%2==0) {
			for (int i=0; i<args.length-1; i+=2) {
				Object k = args[i];
				if (k!=null && !k.toString().isEmpty()) {
					rowdata.put(k.toString(), args[i+1]);
				}
			}
		}
	}
		
	
	public String buildInsertSql()
	{
		String fields = "";
		String values = "";
		for (Entry<String,Object> e : rowdata.entrySet())
		{
			String k = e.getKey();
			if (k==null || k.isEmpty()) {
				continue;
			}
			Object v = e.getValue();
			
			fields += fields.isEmpty() ?  k : ","+k;
			values += values.isEmpty() ?  "" : ",";	
			if (v==null) {
				values += "NULL";
			}else if (v instanceof String && !v.toString().endsWith("()")) {
				values += "'" + v.toString() + "'";
			}else {  //int, now()
				values += v.toString();
			}
		}
			
		String sql = "";
		if (!fields.isEmpty() && !values.isEmpty()) {
			sql = "INSERT INTO " + tbName + " (" + fields + ") VALUES (" + values + ")";
		}
		return sql;
	}
	
	
	public String buildUpdateSql(String where)
	{
		String values = "";
		for (Entry<String,Object> e : rowdata.entrySet()) {
			String k = e.getKey();
			if (k==null || k.isEmpty()) {
				continue;
			}
			Object v = e.getValue();
			values += values.isEmpty() ?  "" : ",";
			values += k+"=";
			if (v==null) {
				values += "NULL";
			}else if (v instanceof String && !v.toString().endsWith("()")) {
				values += "'" + v.toString() + "'";
			}else {  //int, now()
				values += v.toString();
			}
		}
		
		String sql = "";
		if (!values.isEmpty()) {
			sql = "UPDATE " + tbName + " SET " + values;
			if (where!=null && !where.isEmpty()) {
				sql += " WHERE " + where;
			}
		}
		
		return sql;
	}
	
	
	public String buildDeleteSql(String where)
	{
		String sql = "DELETE FROM " + tbName;
		if (where!=null && !where.isEmpty()) {
			sql += " WHERE " + where;
		}
		
		return sql;
	}
	

}
