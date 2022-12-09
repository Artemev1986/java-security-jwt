package com.mikarte.javasecurityjwt.repository;

import com.mikarte.javasecurityjwt.model.Message;
import com.mikarte.javasecurityjwt.model.User;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@DataJpaTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class MessageRepositoryTest {

    private final TestEntityManager entityManager;

    private final MessageRepository messageRepository;

    private final User user = new User();
    private final User user1 = new User();
    private final Message message = new Message();
    private final Message message1 = new Message();
    private final Message message2 = new Message();
    private final Message message3 = new Message();
    private final Pageable page = PageRequest.of(0, 2, Sort.by("id").descending());

    @BeforeEach
    void beforeEach() {
        user.setName("Mikhail");
        user.setPassword("password");

        user1.setName("Alex");
        user1.setPassword("password1");

        entityManager.persist(user);
        entityManager.persist(user1);

        message.setName("Mikhail");
        message.setMessage("text1");

        message1.setName("Mikhail");
        message1.setMessage("text2");

        message2.setName("Mikhail");
        message2.setMessage("text3");

        message3.setName("Alex");
        message3.setMessage("text4");

        entityManager.persist(message);
        entityManager.persist(message1);
        entityManager.persist(message2);
        entityManager.persist(message3);
    }
    @Test
    void findByName() {
        List<Message> messageList = messageRepository.findByName(message.getName(), page);

        assertThat(messageList).isEqualTo(List.of(message2, message1));
    }
}