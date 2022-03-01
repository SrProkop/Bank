package ru.t1.bank.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ru.t1.bank.exceptions.NotFoundException;
import ru.t1.bank.models.Account;
import ru.t1.bank.models.Currency;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class AccountRepositoryImpl implements AccountRepositoryCustom{

    private final EntityManager em;

    public AccountRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Page<Account> findAccountByCurrencyAndMoney(Pageable pageable, Currency currency, BigDecimal fromSum, BigDecimal upToSum) throws NotFoundException {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Account> cq = cb.createQuery(Account.class);

        Root<Account> account = cq.from(Account.class);
        List<Predicate> predicates = new ArrayList<>();

        if (currency != null) {
            predicates.add(cb.equal(account.get("currency"), currency));
        }

        if (upToSum != null) {
            predicates.add(cb.le(account.get("money"), upToSum));
        }

        if (fromSum != null) {
            predicates.add(cb.ge(account.get("money"), fromSum));
        }

        cq.where(predicates.toArray(new Predicate[0]));

        List<Account> accounts = em.createQuery(cq).getResultList();
        Collections.reverse(accounts);

        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), accounts.size());
        try {
            return new PageImpl<>(accounts.subList(start, end), pageable, accounts.size());
        } catch (IllegalArgumentException e) {
            System.out.println(e);
            throw new NotFoundException("Page not found");
        }
    }
}
