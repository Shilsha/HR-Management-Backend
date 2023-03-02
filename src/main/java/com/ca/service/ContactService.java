package com.ca.service;

import com.ca.entity.Contact;
import com.ca.exception.BadReqException;
import com.ca.repository.ContactRepository;
import com.ca.utils.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;
    private Logger logger = LoggerFactory.getLogger(UserService.class);

    public Contact addContact(Contact contact) {

        logger.info("Add new contact");
        contact.setStatus(true);
        contact.setResponse(Response.OPEN);
        return contactRepository.save(contact);
    }

    public Contact getContactById(Long contactId) {

        logger.info("Get Contact by ContactId {}", contactId);
        Optional<Contact> contact = contactRepository.findByContactId(contactId);
        if (!contact.isPresent()){
            throw new BadReqException("Contact not present in DB");
        }

        return contact.get();
    }

    public Contact updateContact(Contact contact) {
        logger.info("Updating Contact id {}", contact.getContactId());

        Optional<Contact> contact1 = contactRepository.findByContactId(contact.getContactId());
        if (!contact1.isPresent()){
            throw new BadReqException("Contact not present in DB");
        }

        return contactRepository.save(contact);

    }

    public Contact deleteContact(Long contactId) {
        logger.info("Delete Contact id {}",contactId);

        Optional<Contact> contact = contactRepository.findByContactId(contactId);
        if (!contact.isPresent()){
            throw new BadReqException("Contact not present in DB");
        }

        Contact contact1 = contact.get();
        contact1.setStatus(false);
        contact1.setResponse(Response.REJECT);
        return contactRepository.save(contact1);
    }
}
