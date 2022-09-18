package com.dsm.banking.dto;

import java.util.List;

import lombok.Data;

@Data
public class AccountHistoryDto {
	private String accountId;
	private double balance;
	private int currentPage;
	private int totalPage;
	private int PageSize;
	private List<AccountOperationDto>accountOperationDtos;

}
