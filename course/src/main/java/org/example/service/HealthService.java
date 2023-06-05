package org.example.service;

import com.mfa.framework.base.constant.ResultCodes;
import com.mfa.framework.base.exception.ApplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HealthService {

    public void checkHealth(Long action) {
        if (action == 1) {
            throw new ApplicationException(ResultCodes.TYPE_MISMATCH);
        } else {
            System.out.println(action);
        }
    }
}
