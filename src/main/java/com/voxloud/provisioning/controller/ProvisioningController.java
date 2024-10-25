package com.voxloud.provisioning.controller;

import com.voxloud.provisioning.service.contracts.ProvisioningService;
import com.voxloud.provisioning.validation.ValidMacAddress;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling provisioning requests.
 * This controller exposes API endpoints to managing provisioning files based on MAC addresses.
 */
@Validated
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProvisioningController {

    private final ProvisioningService provisioningService;

    /**
     * Fetches the provisioning file for a given MAC address.
     *
     * @param macAddress the MAC address for which the provisioning file is requested.
     * @return a ResponseEntity containing the provisioning file as a String, or an error response
     * if the MAC address is invalid or if the provisioning file cannot be retrieved.
     */
    @Operation(summary = "Fetches the provisioning file for a given MAC address",
            description = "Provide a valid MAC address to retrieve its provisioning file.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched the provisioning file."),
            @ApiResponse(responseCode = "400", description = "Invalid MAC address format.",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", description = "Unable to locate the device with MAC address.",
                    content = @Content(schema = @Schema(hidden = true)))
    })
    @GetMapping("/provisioning/{macAddress}")
    public ResponseEntity<String> fetchProvisioningFile(
            @Parameter(description = "The MAC address of the device", example = "00:1A:2B:3C:4D:5E", required = true)
            @ValidMacAddress @PathVariable String macAddress) {
        return ResponseEntity.ok(provisioningService.getProvisioningFile(macAddress));
    }
}