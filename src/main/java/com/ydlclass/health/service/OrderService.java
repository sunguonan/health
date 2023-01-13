package com.ydlclass.health.service;

import java.util.Map;

/**
 * @author sunGuoNan
 * @version 1.0
 */
public interface OrderService {

    Integer submit(Map<String, String> map) throws Exception;

    Map<String, Object> findById(Integer id) throws Exception;
}
