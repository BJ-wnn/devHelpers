package com.github.nan.dataprocess.service;

import java.io.File;
import java.util.List;

public interface FileProcessor {

    List<File> getFilesToProcess(String directoryPath, String fileExtension);
}