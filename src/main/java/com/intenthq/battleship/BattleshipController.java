package com.intenthq.battleship;

import com.intenthq.battleship.game.IInputProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BattleshipController {

    public static final String OUTPUT_ATT = "output";
    @Autowired
    private IInputProcessor inputProcessor;


    @RequestMapping("/battleship")
    public String battleship(ModelMap model) {
        return "battleship";
    }

    @RequestMapping("/battleship/exercise")
    public String exercise(@RequestParam(value = "input", required = false) String input, ModelMap model) {

        if (!StringUtils.isEmpty(input)) {
            String output = inputProcessor.processInput(input);


            model.addAttribute(OUTPUT_ATT, output);
        }
        return "exercise";
    }

    public void setInputProcessor(IInputProcessor inputProcessor) {
        this.inputProcessor = inputProcessor;
    }
}
