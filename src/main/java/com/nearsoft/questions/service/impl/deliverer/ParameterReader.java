package com.nearsoft.questions.service.impl.deliverer;

import com.nearsoft.questions.error.ParameterMissingException;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ParameterReader {

    public Long getLong(Map<String, String> parametersMap, String parameterName) {

        if(!parametersMap.containsKey(parameterName)){
            throw new ParameterMissingException(parameterName);
        }

        Long longValue = Long.parseLong(parametersMap.get(parameterName));

        return longValue;
    }

    public String getString(Map<String, String> parametersMap, String parameterName) {
        if(!parametersMap.containsKey(parameterName)){
            throw new ParameterMissingException(parameterName);
        }

        return parametersMap.get(parameterName);
    }
}
