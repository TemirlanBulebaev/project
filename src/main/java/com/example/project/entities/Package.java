package com.example.project.entities;

import javax.persistence.*;

@Entity
@Table(name = "PACKAGE")
public class Package{

    @Id
    @Column(name = "PACKAGE_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "package_seq")
    @SequenceGenerator(name = "package_seq", allocationSize = 1)
    private Long id;

    @Column(name = "NAME")
    @Enumerated(value = EnumType.STRING)
    private PackageType name;

    @Column(name = "AMOUNT")
    private Long amount;

    public Package() {
    }

    public Package(PackageType name, Long amount) {
        this.name = name;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PackageType getName() {
        return name;
    }

    public void setName(PackageType name) {
        this.name = name;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
