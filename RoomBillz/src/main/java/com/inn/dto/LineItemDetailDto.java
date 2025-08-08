package com.inn.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "LineItemDetailDto", description = "Data transfer object for Line Item Detail")
public class LineItemDetailDto {

    @Schema(description = "Item Name", example = "Sugar", required = true)
    @NotEmpty(message = "Item name cannot be null or empty")
    @Size(min = 2, max = 30, message = "The length of item name should be between 2 to 30")
    private String itemName;

    @Schema(description = "Total item price", example = "150.00")
    @NotNull(message = "Item price cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Item price must be greater than 0")
    private Double itemPrice;

    @Schema(description = "Unit price of the item", example = "30.00")
    @NotNull(message = "Unit price cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Unit price must be greater than 0")
    private Double unitPrice;

    @Schema(description = "Quantity with unit", example = "5 Kg", required = true)
    @NotEmpty(message = "Quantity cannot be null or empty")
    @Size(min = 1, max = 20, message = "The length of quantity should be between 1 to 20")
    private String quantity;
}
