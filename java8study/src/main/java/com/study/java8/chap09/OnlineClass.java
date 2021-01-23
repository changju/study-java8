package com.study.java8.chap09;

import java.util.Optional;

public class OnlineClass {
	private Integer id;
	private String title;
	private boolean closed;
	public Progress progress;

	public OnlineClass(Integer id, String title, boolean closed) {
		this.id = id;
		this.title = title;
		this.closed = closed;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean getClosed() {
		return closed;
	}

	public void setId(boolean closed) {
		this.closed = closed;
	}

	public Optional<Progress> getProgress() {
		// return Optional.of(progress); // 뒤에 오는것이 null이 아닌 것이라 가정
		return Optional.ofNullable(progress); // null 일 수 있는 값을 이렇게 넣는다.
	}

	public void setProgress(Progress progress) {
		this.progress = progress;
	}

	// 아래 코드는 위험하다. 이유는 setProgress 호출할때 null을 넣어 호출 할 수 있기 때문이다.
	// spring_boot.setProgress(null);
	public void setProgress(Optional<Progress> progress) {
		progress.ifPresent(p -> this.progress = p);
	}

	public boolean isClosed() {
		return closed;
	}

}
