/**
 * Copyright (C) 2015 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.strata.finance.fx;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.joda.beans.Bean;
import org.joda.beans.BeanDefinition;
import org.joda.beans.ImmutableBean;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectFieldsBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import com.opengamma.strata.basics.PayReceive;
import com.opengamma.strata.basics.currency.Currency;
import com.opengamma.strata.basics.currency.CurrencyAmount;

/**
 * A single payment of a known amount on a specific date.
 * <p>
 * This class represents a payment, where the payment date and amount are known.
 */
@BeanDefinition
public final class FxPayment
    implements ImmutableBean, Serializable {

  /**
   * The date that the payment is made.
   * <p>
   * This date should be a valid business day.
   */
  @PropertyDefinition(validate = "notNull")
  private final LocalDate paymentDate;
  /**
   * The value of the payment.
   * <p>
   * The payment value is signed.
   * A negative value indicates a payment while a positive value indicates receipt.
   */
  @PropertyDefinition(validate = "notNull")
  private final CurrencyAmount value;

  //-------------------------------------------------------------------------
  /**
   * Creates a {@code FxPayment} representing an amount.
   * <p>
   * Whether the payment is pay or receive is determined by the sign of the specified amonut.
   * 
   * @param date  the date that the payment is made
   * @param value  the amount of the payment
   * @return the payment instance
   */
  public static FxPayment of(LocalDate date, CurrencyAmount value) {
    return new FxPayment(date, value);
  }

  /**
   * Creates a {@code FxPayment} representing an amount to be paid.
   * <p>
   * The sign of the amount will be normalized to be negative, indicating a payment.
   * 
   * @param date  the date that the payment is made
   * @param value  the amount of the payment
   * @return the payment instance
   */
  public static FxPayment ofPay(LocalDate date, CurrencyAmount value) {
    return new FxPayment(date, value.negative());
  }

  /**
   * Creates a {@code FxPayment} representing an amount to be received.
   * <p>
   * The sign of the amount will be normalized to be positive, indicating receipt.
   * 
   * @param date  the date that the payment is made
   * @param value  the amount of the payment
   * @return the payment instance
   */
  public static FxPayment ofReceive(LocalDate date, CurrencyAmount value) {
    return new FxPayment(date, value.positive());
  }

  //-------------------------------------------------------------------------
  /**
   * Gets the currency of the payment.
   * <p>
   * This simply returns {@code getValue().getCurrency()}.
   * 
   * @return the currency of the payment
   */
  public Currency getCurrency() {
    return value.getCurrency();
  }

  /**
   * Gets the amount of the payment.
   * <p>
   * The payment value is signed.
   * A negative value indicates a payment while a positive value indicates receipt.
   * <p>
   * This simply returns {@code getValue().getAmount()}.
   * 
   * @return the amount of the payment
   */
  public double getAmount() {
    return value.getAmount();
  }

  /**
   * Gets a flag indicating whether the value is to be paid or received.
   * 
   * @return the pay receive flag
   */
  public PayReceive getPayReceive() {
    return PayReceive.ofSignedAmount(value.getAmount());
  }

  /**
   * Returns the inverse payment.
   * <p>
   * The inverse payment swaps the sign of the currency amount.
   * 
   * @return the inverse exchange
   */
  public FxPayment inverse() {
    return new FxPayment(paymentDate, value.negated());
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code FxPayment}.
   * @return the meta-bean, not null
   */
  public static FxPayment.Meta meta() {
    return FxPayment.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(FxPayment.Meta.INSTANCE);
  }

  /**
   * The serialization version id.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Returns a builder used to create an instance of the bean.
   * @return the builder, not null
   */
  public static FxPayment.Builder builder() {
    return new FxPayment.Builder();
  }

  private FxPayment(
      LocalDate paymentDate,
      CurrencyAmount value) {
    JodaBeanUtils.notNull(paymentDate, "paymentDate");
    JodaBeanUtils.notNull(value, "value");
    this.paymentDate = paymentDate;
    this.value = value;
  }

  @Override
  public FxPayment.Meta metaBean() {
    return FxPayment.Meta.INSTANCE;
  }

  @Override
  public <R> Property<R> property(String propertyName) {
    return metaBean().<R>metaProperty(propertyName).createProperty(this);
  }

  @Override
  public Set<String> propertyNames() {
    return metaBean().metaPropertyMap().keySet();
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the date that the payment is made.
   * <p>
   * This date should be a valid business day.
   * @return the value of the property, not null
   */
  public LocalDate getPaymentDate() {
    return paymentDate;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the value of the payment.
   * <p>
   * The payment value is signed.
   * A negative value indicates a payment while a positive value indicates receipt.
   * @return the value of the property, not null
   */
  public CurrencyAmount getValue() {
    return value;
  }

  //-----------------------------------------------------------------------
  /**
   * Returns a builder that allows this bean to be mutated.
   * @return the mutable builder, not null
   */
  public Builder toBuilder() {
    return new Builder(this);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      FxPayment other = (FxPayment) obj;
      return JodaBeanUtils.equal(getPaymentDate(), other.getPaymentDate()) &&
          JodaBeanUtils.equal(getValue(), other.getValue());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash = hash * 31 + JodaBeanUtils.hashCode(getPaymentDate());
    hash = hash * 31 + JodaBeanUtils.hashCode(getValue());
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(96);
    buf.append("FxPayment{");
    buf.append("paymentDate").append('=').append(getPaymentDate()).append(',').append(' ');
    buf.append("value").append('=').append(JodaBeanUtils.toString(getValue()));
    buf.append('}');
    return buf.toString();
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code FxPayment}.
   */
  public static final class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code paymentDate} property.
     */
    private final MetaProperty<LocalDate> paymentDate = DirectMetaProperty.ofImmutable(
        this, "paymentDate", FxPayment.class, LocalDate.class);
    /**
     * The meta-property for the {@code value} property.
     */
    private final MetaProperty<CurrencyAmount> value = DirectMetaProperty.ofImmutable(
        this, "value", FxPayment.class, CurrencyAmount.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "paymentDate",
        "value");

    /**
     * Restricted constructor.
     */
    private Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case -1540873516:  // paymentDate
          return paymentDate;
        case 111972721:  // value
          return value;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public FxPayment.Builder builder() {
      return new FxPayment.Builder();
    }

    @Override
    public Class<? extends FxPayment> beanType() {
      return FxPayment.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code paymentDate} property.
     * @return the meta-property, not null
     */
    public MetaProperty<LocalDate> paymentDate() {
      return paymentDate;
    }

    /**
     * The meta-property for the {@code value} property.
     * @return the meta-property, not null
     */
    public MetaProperty<CurrencyAmount> value() {
      return value;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case -1540873516:  // paymentDate
          return ((FxPayment) bean).getPaymentDate();
        case 111972721:  // value
          return ((FxPayment) bean).getValue();
      }
      return super.propertyGet(bean, propertyName, quiet);
    }

    @Override
    protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
      metaProperty(propertyName);
      if (quiet) {
        return;
      }
      throw new UnsupportedOperationException("Property cannot be written: " + propertyName);
    }

  }

  //-----------------------------------------------------------------------
  /**
   * The bean-builder for {@code FxPayment}.
   */
  public static final class Builder extends DirectFieldsBeanBuilder<FxPayment> {

    private LocalDate paymentDate;
    private CurrencyAmount value;

    /**
     * Restricted constructor.
     */
    private Builder() {
    }

    /**
     * Restricted copy constructor.
     * @param beanToCopy  the bean to copy from, not null
     */
    private Builder(FxPayment beanToCopy) {
      this.paymentDate = beanToCopy.getPaymentDate();
      this.value = beanToCopy.getValue();
    }

    //-----------------------------------------------------------------------
    @Override
    public Object get(String propertyName) {
      switch (propertyName.hashCode()) {
        case -1540873516:  // paymentDate
          return paymentDate;
        case 111972721:  // value
          return value;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
    }

    @Override
    public Builder set(String propertyName, Object newValue) {
      switch (propertyName.hashCode()) {
        case -1540873516:  // paymentDate
          this.paymentDate = (LocalDate) newValue;
          break;
        case 111972721:  // value
          this.value = (CurrencyAmount) newValue;
          break;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
      return this;
    }

    @Override
    public Builder set(MetaProperty<?> property, Object value) {
      super.set(property, value);
      return this;
    }

    @Override
    public Builder setString(String propertyName, String value) {
      setString(meta().metaProperty(propertyName), value);
      return this;
    }

    @Override
    public Builder setString(MetaProperty<?> property, String value) {
      super.setString(property, value);
      return this;
    }

    @Override
    public Builder setAll(Map<String, ? extends Object> propertyValueMap) {
      super.setAll(propertyValueMap);
      return this;
    }

    @Override
    public FxPayment build() {
      return new FxPayment(
          paymentDate,
          value);
    }

    //-----------------------------------------------------------------------
    /**
     * Sets the {@code paymentDate} property in the builder.
     * @param paymentDate  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder paymentDate(LocalDate paymentDate) {
      JodaBeanUtils.notNull(paymentDate, "paymentDate");
      this.paymentDate = paymentDate;
      return this;
    }

    /**
     * Sets the {@code value} property in the builder.
     * @param value  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder value(CurrencyAmount value) {
      JodaBeanUtils.notNull(value, "value");
      this.value = value;
      return this;
    }

    //-----------------------------------------------------------------------
    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder(96);
      buf.append("FxPayment.Builder{");
      buf.append("paymentDate").append('=').append(JodaBeanUtils.toString(paymentDate)).append(',').append(' ');
      buf.append("value").append('=').append(JodaBeanUtils.toString(value));
      buf.append('}');
      return buf.toString();
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
