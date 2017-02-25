package com.codeborne.selenide.impl;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Proxy;
import java.util.List;

import static com.codeborne.selenide.Condition.visible;

public class CollectionElementByCondition extends WebElementSource {

  public static SelenideElement wrap(WebElementsCollection collection, Condition condition) {
    return (SelenideElement) Proxy.newProxyInstance(
        collection.getClass().getClassLoader(), new Class<?>[]{SelenideElement.class},
        new SelenideElementProxy(new CollectionElementByCondition(collection, condition)));
  }

  private final WebElementsCollection collection;
  private final Condition condition;

  CollectionElementByCondition(WebElementsCollection collection, Condition condition) {
    this.collection = collection;
    this.condition = condition;
  }

  @Override
  public WebElement getWebElement() {
    List<WebElement> list = collection.getActualElements();

    for (WebElement element : list) {
      if (condition.apply(element)) {
        return element;
      }
    }

    throw new ElementNotFound(getSearchCriteria(), condition);
  }

  @Override
  public String getSearchCriteria() {
    return collection.description() + ".findBy(" + condition + ")";
  }


  @Override
  public ElementNotFound createElementNotFoundError(Condition condition, Throwable lastError) {
    if (collection.getActualElements().isEmpty()) {
      return new ElementNotFound(collection.description(), visible, lastError);
    }
    return super.createElementNotFoundError(condition, lastError);
  }

  @Override
  public String toString() {
    return getSearchCriteria();
  }

}
