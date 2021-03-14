package com.livingreen.bot;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.livingreen.dto.Dto;
import com.livingreen.keys.Keys0;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.ResponseBody;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class Bot extends TelegramLongPollingBot {



//keyboard
    List<String> keys = new ArrayList<>();
    public StringBuilder answer = new StringBuilder();



    static OkHttpClient client = new OkHttpClient();
    static ObjectMapper mapper = new ObjectMapper();


        //Input section --------------------------------
        @SneakyThrows
        @Override
        public void onUpdateReceived(Update update) {
            long chat_id = update.getMessage().getChatId();
            SendMessage message = new SendMessage();
            message.setChatId(chat_id);

                //general commands
                HashMap<String, Callable<SendMessage>> map = new HashMap<String, Callable<SendMessage>>() {
                    {
                        put("/start", () -> {
                            return methodStart(update, message);
                        });
                        put("GetParams", () -> {
                            return method1(update, message);
                        });




                    }
                };





                try {
                    sendAnswer(map.get(update.getMessage().getText()).call());


                } catch (Exception e) {

                    e.printStackTrace();
                    System.out.println("im here");



                }


            }



//  METHODS----------------------------------------------------------------------------

// /start
        private SendMessage methodStart(Update update, SendMessage message) {
        //keys
        keys.clear();
        Keys0 arr[] = Keys0.values();
        for (Keys0 i : arr) {
            keys.add(i.toString());
        }
        addKeys(message, keys);

        //logic



            //Prepare first message
            answer.setLength(0);
            answer.append("Welcome in LivinGreenBot!\n");


            message.setText(answer.toString());
            return message;

        }

// "SIMPLE"
        private SendMessage method1(Update update, SendMessage message) throws IOException {
            //keys
            keys.clear();
            Keys0 arr[] = Keys0.values();
            for (Keys0 i : arr) {
                keys.add(i.toString());
            }
            addKeys(message, keys);

            //logic
            Dto dto = new Dto();
            Request request = new Request.Builder()
                    .url("http://myneulog.online/myneulog4apps.py?type=GetValues;id=8803;pass=5555")
                    .get()
//                    .addHeader("x-rapidapi-key", "1b965ad24fmsh22ee993fe445cfdp140f29jsn674d5c2e08cd")
//                    .addHeader("x-rapidapi-host", "proxy-orbit1.p.rapidapi.com")
                    .build();
//
            ResponseBody body = client.newCall(request).execute().body();
            String json = body.string();
            System.out.println(json);
            dto = mapper.readValue(json, Dto.class);

            if (dto.getId().equals(null)) {
                answer.setLength(0);
                answer.append("try later");
            } else {
                answer.setLength(0);
                answer.append("Your ID -> " + dto.getId() + "\n");
                dto.getSensors().stream().forEach(s -> {

                    answer.append("param  " + s.getType() + " -> " + s.getValue() + "\n");

                });
            }
//
//            saveCurrentParameters(update.getMessage().getChatId());


            message.setText(answer.toString());
            return message;
        }



    // WEB
    private SendMessage methodWeb(Update update, SendMessage message) throws IOException {
        //keys
        keys.clear();
        Keys0 arr[] = Keys0.values();
        for (Keys0 i : arr) {
            keys.add(i.toString());
        }
        addKeys(message, keys);

        //logic

//        WebClient client = WebClient.builder()
//                .baseUrl("https://proxy-orbit1.p.rapidapi.com/v1/")
//                .defaultHeaders(httpHeaders -> {
//            httpHeaders.set("x-rapidapi-key", "1b965ad24fmsh22ee993fe445cfdp140f29jsn674d5c2e08cd");
//            httpHeaders.set("x-rapidapi-host", "proxy-orbit1.p.rapidapi.com");
//            }).build();
//
//        answer.setLength(0);
//        client.get()
//                .retrieve()
//                /*.onStatus(httpStatus -> HttpStatus.NOT_FOUND.equals(httpStatus),
//                        clientResponse -> Mono.empty())*/
//                .bodyToMono(ProxyDto.class)
//                .subscribe(dto -> answer.append(dto.getCurl().toString()),
//                        err -> System.out.println(err.getMessage()));
//
//                ;


//        client.get()
//                .retrieve()
//                .bodyToMono(ProxyDto.class)
//                .subscribe(dto -> answer.append(dto.getCurl().toString()),
//                        err -> System.out.println(err.getMessage()));

                ;




        //message

        message.setText(answer.toString());
        return message;
    }



    // KEYS ------------------------------------------------------------------------
        private void addKeys(SendMessage message, List<String> keys) {
            ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
            message.setReplyMarkup(replyKeyboardMarkup);
            replyKeyboardMarkup.setSelective(true);
            replyKeyboardMarkup.setResizeKeyboard(true);
            replyKeyboardMarkup.setOneTimeKeyboard(false);

//        //Create list keyboard rows
            List<KeyboardRow> keyboardRows = new ArrayList<KeyboardRow>();
//
//        //Calculate keyboard rows for keys
            Integer indexButton = 0;
            KeyboardRow firstRow = new KeyboardRow();
            KeyboardRow secondRow = new KeyboardRow();
            KeyboardRow thirdRow = new KeyboardRow();
            KeyboardRow fourthRow = new KeyboardRow();




            for (String k : keys) {

                if (indexButton == 0) {
                    firstRow.add(k);
                } else if (indexButton == 1) {
                    secondRow.add(k);
                } else if (indexButton == 2) {
                    thirdRow.add(k);
                } else if (indexButton == 3) {
                    fourthRow.add(k);
                }
                indexButton++;
            }

            keyboardRows.add(firstRow);
            keyboardRows.add(secondRow);
            keyboardRows.add(thirdRow);
            keyboardRows.add(fourthRow);


            replyKeyboardMarkup.setKeyboard(keyboardRows);


        }

        //SendAnswer general
        private void sendAnswer(SendMessage message) {
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
                System.out.println("===========================executeException");
            }
        }






        //Bot service data
        @Value("${bot.name}")
        private String botUsername;

        @Value("${bot.token}")
        private String botToken;

        public String getBotUsername() {
            return botUsername;
        }

        public String getBotToken() {
            return botToken;
        }





}

