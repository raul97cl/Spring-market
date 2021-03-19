package com.market.upload;

import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

	void init();
	
	String store(MultipartFile file);
	
	Stream<Path> loadData();
	
	Path load(String filename);

    Resource loadAsResource(String filename);
    
    void delete(String filename);

    void deleteAll();
}
