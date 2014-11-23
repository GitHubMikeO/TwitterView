package com.company.mjo.View;

import com.company.mjo.Controller.ITwitterController;

/**
 * Created by mjo on 10/17/2014.
 *
 * Interface used for separation of concerns, maintainability and testability.
 */
public interface ITwitterView
{
  /**
   * Appends text in status view.
   * @param status text to display
   */
  public void appendStatus(String status);

  /**
   * Clears text from status view.
   */
  public void clearStatus();

  /**
   * Provides access to ITwitterController interface.
   * @param controller ITwitterController
   */
  public void setController(final ITwitterController controller);

  /**
   * Show/hide ITwitterView UI
   * @param show UI
   */
  public void showForm(boolean show);
}
