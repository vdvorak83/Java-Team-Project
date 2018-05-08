package com.skyforce.goal.model;

import com.skyforce.goal.model.enums.TransactionState;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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

    private String txid;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "amount")
    @NotNull
    private BigDecimal amount;

    private BigDecimal fee;

    @OneToOne
    @JoinColumn(name = "from_wallet")
    private Wallet walletFrom;

    @OneToOne
    @JoinColumn(name = "to_wallet")
    private Wallet walletTo;

    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date date;

    @Enumerated(EnumType.ORDINAL)
    private TransactionState state;
}
