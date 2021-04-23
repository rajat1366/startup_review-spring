package com.StartupReview.controller;


import com.StartupReview.models.Startup;
import com.StartupReview.models.User;
import com.StartupReview.payload.request.StartupRequest;
import com.StartupReview.payload.response.MessageResponse;
import com.StartupReview.security.services.UserDetailsImpl;
import com.StartupReview.service.StartupService;
import com.StartupReview.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/startup")
public class StartupController {

    @Autowired
    StartupService startupService;

    @Autowired
    UserService userService;

    @GetMapping("/")
    public ResponseEntity<?>getStartups(@RequestParam(required = false) String searchData,@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "3") int size){
        Pageable paging = PageRequest.of(page, size);
        if(searchData == null){
            Page<Startup> listofStartups =  startupService.getstartups(paging);
            return ResponseEntity.ok(listofStartups);
        } else {
            Page<Startup> listofStartups =  startupService.getstartupsFromSearchData(searchData,paging);
            return ResponseEntity.ok(listofStartups);
        }

    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> saveStartup(@Valid @RequestBody StartupRequest startupRequest) {
        try {
          //  System.out.println(startup);
            UserDetailsImpl userDetails =
                    (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if (startupService.findByName(startupRequest.getName().toLowerCase())) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Start up already exits!!"));
            }
            User user = userService.findById(userDetails.getId()).orElseThrow(() -> new RuntimeException("Error: User is not found."));

            String string = startupRequest.getLaunchDate();
            SimpleDateFormat format = new SimpleDateFormat("MMM d, yyyy", Locale.ENGLISH); //if month entered in full-text like 'january', use MMMM
            Date launchDate = format.parse(string);

            Startup startup = new Startup(startupRequest.getName(), startupRequest.getDescription(), user, launchDate, LocalDateTime.now());
//            startup.setUser(user);
//            startup.setName(startup.getName().toLowerCase());
//            startup.setDateTime( LocalDateTime.now());

            Startup result = startupService.saveStartup(startup);

            if(result!= null)
                return ResponseEntity.ok(new MessageResponse("Startup added successfully!"));
            else
                return ResponseEntity.badRequest().body(new MessageResponse("Error: Unable to add new startup!"));}
        catch(Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Unable to add new startup!"));
        }

    }
}
