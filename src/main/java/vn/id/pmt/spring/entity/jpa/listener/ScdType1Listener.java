package vn.id.pmt.spring.entity.jpa.listener;


import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import vn.id.pmt.spring.entity.jpa.base.BaseModel;

import java.sql.Timestamp;

/**
 * The type ScdType1 entity listener.
 */
public class ScdType1Listener {

  /**
   * Prepare before create.
   *
   * @param baseModel the base id entity
   */
  @PrePersist
  public void prepareBeforeCreate(BaseModel baseModel) {
    baseModel.setCreatedAt(new Timestamp(System.currentTimeMillis()));
    baseModel.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
    baseModel.setStatus(true);
  }

  /**
   * Prepare before update.
   *
   * @param baseModel the base id entity
   */
  @PreUpdate
  public void prepareBeforeUpdate(BaseModel baseModel) {
    baseModel.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
  }
}
