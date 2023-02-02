package boardService.board.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public abstract class AbstractValidator<T> implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @SuppressWarnings("unchecked")//컴파일러에서 노란 경고 안뜨게함
    @Override
    public void validate(Object target, Errors errors) {
        try {
            doValidate((T) target, errors);
        }catch (RuntimeException e){
            throw e;
        }
    }
    protected abstract void doValidate(final T dto, final Errors errors);
}
