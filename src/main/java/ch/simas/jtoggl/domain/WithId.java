package ch.simas.jtoggl.domain;

/**
 * Contract for having ID.
 */
public interface WithId extends Cloneable {

    /**
     * @return Object unique identifier.
     */
    Long getId();

    void setId(Long id);

}
