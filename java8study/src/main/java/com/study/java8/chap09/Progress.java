package com.study.java8.chap09;

import java.time.Duration;
import java.util.Optional;

public class Progress {
	private Duration studyDuration;
	private boolean finished;

	public Duration getStudyDuration() {
		return studyDuration;
	}

	public void setStudyDuration(Duration studyDuration) {
		studyDuration = studyDuration;
	}

    // studyDuration 가 null 일 수 있기에 ofNullable 을 쓰도록 한다.	
	public Optional<Duration> getDuration() {
		return Optional.ofNullable(studyDuration);
	}
	
}
