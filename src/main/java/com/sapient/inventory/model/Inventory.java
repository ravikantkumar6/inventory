package com.sapient.inventory.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ToString
//@Builder
@Entity(name = "Inventory")
@NoArgsConstructor
@AllArgsConstructor
public class Inventory implements Serializable {
    private static final long serialVersionUID = -558043294043707772L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column
    private String productId;
    @Column
    private Integer inventory;
    @Column
    private LocalDateTime updatedDate;
    @Column
    private LocalDateTime createdDate;

}
