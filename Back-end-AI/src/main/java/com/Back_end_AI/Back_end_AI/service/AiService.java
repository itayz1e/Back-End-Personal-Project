package com.Back_end_AI.Back_end_AI.service;


import org.springframework.stereotype.Service;

@Service
public class AiService {

        public String searchProducts(String keyword) {
            return "Searched for:" + keyword;
        }
}
