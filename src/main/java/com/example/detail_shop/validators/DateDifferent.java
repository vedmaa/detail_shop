package com.example.Shop.validators;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;
import java.util.Calendar;
import java.util.Date;

@Documented
@Constraint(validatedBy = DateDifferentValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DateDifferent {

    int minYears() default Integer.MIN_VALUE;
    String message() default "Некорректная дата";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

class DateDifferentValidator implements ConstraintValidator<DateDifferent, Date> {
    private int minYears = Integer.MIN_VALUE;

    @Override
    public void initialize(DateDifferent constraintAnnotation) {
    }

    @Override
    public boolean isValid(Date date, ConstraintValidatorContext constraintValidatorContext) {
        if (date == null) return false;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int years = yearUntil(calendar);
        return years >= minYears;
    }

    private int yearUntil(Calendar date) {
        Calendar calendar = Calendar.getInstance();
        int years = date.get(Calendar.YEAR) - calendar.get(Calendar.YEAR);
        System.out.println(Calendar.YEAR);
        if (date.get(Calendar.MONTH) < calendar.get(Calendar.MONTH) ||
                (date.get(Calendar.MONTH) == calendar.get(Calendar.MONTH) &&
                        date.get(Calendar.DATE) < calendar.get(Calendar.DATE))) {
                years -= 1;
        }
        return years;
    }
}
