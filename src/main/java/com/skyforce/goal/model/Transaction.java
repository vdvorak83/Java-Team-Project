package com.skyforce.goal.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

//    @Column(name = "user_id")
    @ManyToOne
    private User user;

    @Column(name = "amount")
    private BigDecimal amount;

    @ManyToOne
    private Wallet fromWallet;

    @ManyToOne
    private Wallet toWallet;

    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
}
