package domain.validation;

import domain.Friendship;

import java.util.Objects;

/**
 * Friendship class validator
 */
public class FriendshipValidator implements Validator<Friendship>{

    @Override
    public void validate(Friendship entity) throws ValidationException {
        if(Objects.equals(entity.getU1().getID(), entity.getU2().getID()))
            throw new ValidationException(" the ids must be different");
    }
}
