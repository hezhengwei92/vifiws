package net.eoutech.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextHelper implements ApplicationContextAware {
	private static ApplicationContext context;

	@Override
	public void setApplicationContext(ApplicationContext contex) throws BeansException {
		ApplicationContextHelper.context = contex;
	}

	public ApplicationContext getContext() {
		return context;
	}

}
