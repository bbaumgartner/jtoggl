package ch.simas.jtoggl.domain;

/**
 * Created by hpa on 17.3.16.
 */
public interface WithUser {
    User getUser();

    void setUser(User user);

    Long getUserId();

    void setUserId(Long userId);
}
