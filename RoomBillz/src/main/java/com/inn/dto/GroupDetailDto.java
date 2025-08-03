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
@Schema(name = "GroupDetailDto", description = "Data transfer object for Group Detail")
public class GroupDetailDto {

	@Schema(description = "Unique Group Name", example = "RoomBillz", required = true)
	@NotEmpty(message = "Group Name can not be a null or empty")
	@Size(min = 2, max = 30, message = "The length of Group Name should be between 2 to 30")
	private String groupName;
	
}
