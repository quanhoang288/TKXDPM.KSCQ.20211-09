package com.example.bike.subsystem;

import com.example.bike.entity.payment.CreditCard;
import com.example.bike.entity.payment.PaymentTransaction;
import com.example.bike.exception.PaymentException;

/**
 * The {@code InterbankInterface} class is used to communicate with the
 * {@link com.example.bike.subsystem.InterbankSubsystem} to make transaction
 *
 */
public interface InterbankInterface {

    /**
     *
     * @param card
     * @param amount
     * @param contents
     * @return
     * @throws PaymentException
     */
    public abstract PaymentTransaction performPayment(CreditCard card, int amount, String contents)
            throws PaymentException;

    /**
     *
     * @param card
     * @param amount
     * @param contents
     * @return
     * @throws PaymentException
     */
    public abstract PaymentTransaction performRefund(CreditCard card, int amount, String contents)
        throws PaymentException;
}
