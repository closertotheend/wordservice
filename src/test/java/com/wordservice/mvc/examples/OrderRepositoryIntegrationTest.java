/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wordservice.mvc.examples;

import org.hamcrest.Matcher;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.wordservice.mvc.examples.CoreMatchers.with;
import static com.wordservice.mvc.examples.OrderMatchers.LineItem;
import static com.wordservice.mvc.examples.OrderMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class OrderRepositoryIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    OrderRepository repository;

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ProductRepository productRepository;

    @Test
    @Ignore
    public void createOrder() {

        Customer dave = customerRepository.findByEmailAddress(new EmailAddress("dave@dmband.com").getEmail());
        Product iPad = productRepository.findByPropertyValue("name", "iPad");

        Order order = new Order(dave);
        order.add(new LineItem(order, iPad));

        order = repository.save(order);
        assertThat(order.getId(), is(notNullValue()));
    }

    @Ignore
    @Test
    public void readOrder() {

        final EmailAddress email = new EmailAddress("dave@dmband.com");

        List<Order> orders = repository.findByCustomerEmailAddress(email.getEmail());

        assertThat(orders, hasSize(1));

        Matcher<LineItem> twoIPads = allOf(product(CoreMatchers.named("iPad")), amount(2));
        Matcher<LineItem> singleMacBook = allOf(product(CoreMatchers.named("MacBook Pro")), amount(1));

        assertThat(orders, containsOrder(with(LineItem(twoIPads))));
        assertThat(orders, containsOrder(with(LineItem(singleMacBook))));
    }


}
