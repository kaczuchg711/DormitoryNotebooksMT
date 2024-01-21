package kaczuch.master_thesis;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Map;
@Component
public class AuxiliaryFuns {

    public static void showAllParametersInRequest(HttpServletRequest request) {
        Map<String, String[]> parameters = request.getParameterMap();
        for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
            String paramName = entry.getKey();
            String[] paramValues = entry.getValue();

            // Przetwarzanie każdej wartości parametru
            for (String value : paramValues) {
                System.out.println("Parametr: " + paramName + ", Wartość: " + value);
            }
        }
    }
}
