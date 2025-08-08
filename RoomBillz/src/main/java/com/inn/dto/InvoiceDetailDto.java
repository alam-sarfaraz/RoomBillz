package com.inn.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "InvoiceDetailDto", description = "Data transfer object for Invoice Detail")
public class InvoiceDetailDto {

    @Schema(description = "Name of the invoice file", example = "invoice_2025_08_06.pdf", required = true)
    @NotEmpty(message = "File name cannot be null or empty")
    @Size(min = 3, max = 100, message = "The length of file name should be between 3 to 100 characters")
    private String fileName;
}
