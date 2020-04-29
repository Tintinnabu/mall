package top.tinn.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @Description FlagValidatorClass
 * @Author Tinn
 * @Date 2020/4/8 16:16
 */
public class FlagValidatorClass implements ConstraintValidator<FlagValidator, Integer> {
   private String[] values;
   public void initialize(FlagValidator flagValidator) {
      this.values=flagValidator.value();
   }

   @Override
   public boolean isValid(Integer value, ConstraintValidatorContext constraintValidatorContext) {
      if (value==null) return true;
      for(String s:values){
         if (s.equals(String.valueOf(value))){
            return true;
         }
      }
      return false;
   }

}
