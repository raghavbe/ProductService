package com.capstone.productservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Getter
@Setter
@Entity
public class Category extends Base
{
    @OneToMany(mappedBy = "category")
    @Fetch(FetchMode.SUBSELECT)
    List<Product> products;
}