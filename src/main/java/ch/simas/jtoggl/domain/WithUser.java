package ch.simas.jtoggl.domain;

/**
 * Contract ot have reference to User.
 */
public interface WithUser {

    /**
     * @return User object.
     */
    User getUser();

    void setUser(User user);

    /**
     * @return User ID.
     */
    Long getUserId();

    void setUserId(Long userId);
}
