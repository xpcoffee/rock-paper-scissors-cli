package com.xpcoffee.rock_paper_scissors.api;

import com.xpcoffee.rock_paper_scissors.GameException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends GameException {
}
