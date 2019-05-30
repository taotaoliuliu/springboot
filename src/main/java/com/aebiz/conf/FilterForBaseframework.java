package com.aebiz.conf;

import java.io.IOException;

import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

public class FilterForBaseframework implements TypeFilter {

	@Override
	public boolean match(MetadataReader reader, MetadataReaderFactory rf) throws IOException {
		String str = reader.getResource().getURI().toString();
		if (str != null) {
			str = str.replaceAll("\\/", ".");
			if (str.contains("com.aebiz.queue")|| str.contains("com.aebiz.common.storm") || str.contains("org.springframework.boot.autoconfigure.transaction.jta.JtaAutoConfiguration")) {
				return true;
			}
		}
		return false;
	}

}
