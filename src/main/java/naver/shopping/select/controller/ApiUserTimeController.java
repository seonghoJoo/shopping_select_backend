package naver.shopping.select.controller;


import naver.shopping.select.dto.response.ApiUseTimeDto;
import naver.shopping.select.model.ApiUseTime;
import naver.shopping.select.repository.ApiUseTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ApiUserTimeController {

    private ApiUseTimeRepository apiUseTimeRepository;

    @Autowired
    public ApiUserTimeController(ApiUseTimeRepository apiUseTimeRepository){
        this.apiUseTimeRepository = apiUseTimeRepository;
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/api/user/time")
    public List<ApiUseTimeDto> getAllApiUseTime(){

        List<ApiUseTime> apiUseTimeList = apiUseTimeRepository.findAll();
        List<ApiUseTimeDto> apiUseTimeDtos = new ArrayList<>();
        for(ApiUseTime apiUseTime : apiUseTimeList){
            ApiUseTimeDto dto = new ApiUseTimeDto();
            dto.setUsername(apiUseTime.getUser().getUsername());
            dto.setTotalUseTime(apiUseTime.getTotalTime());
            apiUseTimeDtos.add(dto);
        }

        return apiUseTimeDtos;
    }


}
