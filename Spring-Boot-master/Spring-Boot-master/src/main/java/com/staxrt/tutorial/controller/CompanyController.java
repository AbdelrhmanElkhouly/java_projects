/*
 *
 *  Copyright (c) 2018-2020 Givantha Kalansuriya, This source is a part of
 *   Staxrt - sample application source code.
 *   http://staxrt.com
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package com.staxrt.tutorial.controller;

import com.staxrt.tutorial.exception.ResourceNotFoundException;
import com.staxrt.tutorial.model.User;
import com.staxrt.tutorial.controller.AppLoader;
import com.staxrt.tutorial.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.*;
import java.io.*;
import org.apache.commons.io.IOUtils;
import javax.servlet.*;
import java.util.Enumeration;
import java.io.IOException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
/**
 * The type User controller.
 *
 * @author Givantha Kalansuriya
 */
@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class CompanyController {

  @Autowired
  private UserRepository userRepository;

  /**
   * Get all users list.
   *
   * @return the string
   */
  @GetMapping("/companies")
  public String getAllUsers() {
    AppLoader app = new AppLoader();
    return app.listData();
  }

  @GetMapping("/companyCount")
  public String listCompanyCount() {
    AppLoader app = new AppLoader();
    return app.listCompanyCount();
  }

  @GetMapping("/jobCount")
  public String listJobCount() {
    AppLoader app = new AppLoader();
    return app.listJobCount();
  }

  @GetMapping("/mostPopularArea")
  public String mostPopularArea() {
    AppLoader app = new AppLoader();
    return app.mostPopularArea();
  }

  @GetMapping(value = "/mostPopularAreaImage", produces = MediaType.IMAGE_JPEG_VALUE)
  public @ResponseBody byte[] mostPopularAreaImage() throws IOException {
    InputStream in = getClass().getResourceAsStream("src/public/most_popular_area.jpeg");
    return IOUtils.toByteArray(in);
  }


}