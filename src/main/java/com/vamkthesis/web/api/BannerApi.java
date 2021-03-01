package com.vamkthesis.web.api;


import com.vamkthesis.web.api.input.BannerInput;
import com.vamkthesis.web.service.IBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/banner")
public class BannerApi {
    @Autowired
    private IBannerService bannerService;

    @Secured ("ROLE_ADMIN")
    @RequestMapping(value = "/remove/{id}", method = RequestMethod.DELETE)
    public void removeById(@RequestParam long id){
        bannerService.delete(id);
    }

    @Secured ("ROLE_ADMIN")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public BannerInput createCategory(@RequestBody BannerInput bannerInput) {
        return bannerService.save(bannerInput);
    }

    @Secured ("ROLE_ADMIN")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public BannerInput update(@RequestBody BannerInput bannerInput, @PathVariable("id") long id) {
        bannerInput.setId(id);
        return bannerService.save(bannerInput);
    }

}
