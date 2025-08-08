package com.inn.dto;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "PurchaseOrderDetailDto", description = "Data transfer object for Purchase Order Detail")
public class PurchaseOrderDetailDto {

    @Schema(description = "Username", example = "sarfarazalam", required = true)
    @NotEmpty(message = "User name cannot be null or empty")
    @Size(min = 5, max = 30, message = "The length of user name should be between 5 to 30")
    private String userName;

    @Schema(description = "Unique Group Name", example = "RoomBillz", required = true)
    @NotEmpty(message = "Group name cannot be null or empty")
    @Size(min = 2, max = 30, message = "The length of group name should be between 2 to 30")
    private String groupName;

    @Schema(description = "Purchase date in yyyy-MM-dd format", example = "2025-08-06", required = true)
    @NotNull(message = "Purchase date must not be null")
    @PastOrPresent(message = "Purchase date must be today or in the past")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate purchaseDate;

    @Schema(description = "Mode of Payment", example = "Cash/UPI", required = true)
    @NotEmpty(message = "Mode of payment cannot be null or empty")
    @Size(min = 2, max = 30, message = "The length of mode of payment should be between 2 to 30")
    private String modeOfPayment;

    @Schema(description = "List of item details")
    private List<LineItemDetailDto> itemDetails;
}
