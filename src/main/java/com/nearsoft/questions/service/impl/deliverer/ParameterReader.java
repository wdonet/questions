package com.nearsoft.questions.service.impl.deliverer;

import com.nearsoft.questions.error.ParameterMissingException;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ParameterReader {

    public long getLong(Map<String, String> parametersMap, String parameterName) {

        if(!parametersMap.containsKey(parameterName)){
            throw new ParameterMissingException(parameterName);
        }

        return Long.parseLong(parametersMap.get(parameterName));
    }

    public int getInteger(Map<String, String> parametersMap, String parameterName) {

        if(!parametersMap.containsKey(parameterName)){
            throw new ParameterMissingException(parameterName);
        }

        return Integer.parseInt(parametersMap.get(parameterName));
    }

    public String getString(Map<String, String> parametersMap, String parameterName) {
        if(!parametersMap.containsKey(parameterName)){
            throw new ParameterMissingException(parameterName);
        }

        return parametersMap.get(parameterName);
    }
}
