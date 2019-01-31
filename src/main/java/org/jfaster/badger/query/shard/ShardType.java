package org.jfaster.badger.query.shard;

import lombok.Getter;

public enum ShardType {
	
	hash(1,"hash水平方式切分表"),
	range(2,"按字段范围切分表");

	@Getter
	private int type;

	@Getter
	private String desc;
	
	ShardType(int type,String desc){
		this.type = type;
		this.desc = desc;
	}
	
}
