package org.alok.authservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = UserCredential.TABLE_NAME)
public class UserCredential {

    public static final String TABLE_NAME = "USERCREDENTIAL";
    public static final String SEQ_GEN_ALIAS = "seq_gen_altas";
    public static final String SEQ_GEN_STRATEGY = "uuid2";

    @Id
    @GeneratedValue(generator = UserCredential.SEQ_GEN_ALIAS)
    @GenericGenerator(name = UserCredential.SEQ_GEN_ALIAS, strategy = UserCredential.SEQ_GEN_STRATEGY)
    private String userId;
    private String userName;
    private String userEmail;
    private String userPassword;

}
