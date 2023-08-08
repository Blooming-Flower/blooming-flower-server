package kr.co.flower.blooming.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Hello {
	@Id
	private long id;
	private String name;
}
