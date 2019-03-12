package com.aebiz.conf;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;

public class MyMessageConverter extends StringHttpMessageConverter {

	public MyMessageConverter() {

		/*List<MediaType> supportedMediaTypes = new ArrayList<>();
		supportedMediaTypes.add(MediaType.TEXT_XML);
		supportedMediaTypes.add(new MediaType(MediaType.APPLICATION_JSON_VALUE));

		super.setSupportedMediaTypes(supportedMediaTypes);*/

	}
}