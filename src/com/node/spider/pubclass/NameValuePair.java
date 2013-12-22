package com.node.spider.pubclass;

public class NameValuePair<N, V> {

	N name;
	V value;

	public NameValuePair(N name, V value) {
		this.name = name;
		this.value = value;
	}

	public N getName() {
		return name;
	}

	public V getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "pair:" + name.toString() + "-" + value.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof NameValuePair) {
			NameValuePair pair = (NameValuePair) obj;
			return name.equals(pair.name) && value.equals(pair.value);
		}
		return super.equals(obj);
	}
}
