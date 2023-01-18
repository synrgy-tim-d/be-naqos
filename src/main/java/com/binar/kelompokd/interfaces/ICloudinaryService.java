package com.binar.kelompokd.interfaces;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface ICloudinaryService {
  String uploadFile(MultipartFile image);
  File convertMultiPartToFile(MultipartFile file) throws IOException;
}
