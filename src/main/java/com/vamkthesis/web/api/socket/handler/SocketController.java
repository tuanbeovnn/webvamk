//package com.vamkthesis.web.api.socket.handler;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.livestreamapp.config.JwtTokenUtil;
//import com.livestreamapp.convert.Converter;
//import com.livestreamapp.dto.MyUserDTO;
//import com.livestreamapp.dto.OnsaleDTO;
//import com.livestreamapp.entity.ChannelEntity;
//import com.livestreamapp.exception.ClientException;
//import com.livestreamapp.redis.model.*;
//import com.livestreamapp.redis.repository.ChannelLiveRedisRepository;
//import com.livestreamapp.redis.repository.ChannelViewRedisRepository;
//import com.livestreamapp.redis.repository.OnsaleRedisRepository;
//import com.livestreamapp.redis.repository.UserRedisRepository;
//import com.livestreamapp.repository.IChannelRepository;
//import com.livestreamapp.service.impl.ChannelLiveService;
//import com.livestreamapp.service.impl.OnsaleService;
//import com.livestreamapp.socket.config.DataUtils;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.messaging.simp.user.SimpUser;
//import org.springframework.messaging.simp.user.SimpUserRegistry;
//import org.springframework.messaging.support.GenericMessage;
//import org.springframework.stereotype.Controller;
//
//import java.security.Principal;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import java.util.Set;
//
//@Controller
//public class SocketController {
//
//    @Autowired
//    private SimpUserRegistry simple;
//
//
//    @Autowired
//    DataUtils dataUtils;
//
//    @Autowired
//    private JwtTokenUtil jwtTokenUtil;
//
//    @Autowired
//    private ChannelLiveService channelLiveService;
//
//
//    @Autowired
//    private OnsaleService onsaleService;
//
//    @Autowired
//    private ChannelLiveRedisRepository channelLiveRedisRepository;
//
//
//    @Autowired
//    private IChannelRepository channelRepository;
//
//    @Autowired
//    private ChannelViewRedisRepository channelViewRedisRepository;
//
//    @Autowired
//    private OnsaleRedisRepository onsaleRedisRepository;
//
//    @Autowired
//    private UserRedisRepository userRedisRepository;
//
//    @Autowired
//    ObjectMapper objectMapper;
//
//    public Set<SimpUser> getUsers() {
//        return simple.getUsers();
//    }
//
//    @Autowired
//    SimpMessagingTemplate template;
//
//    protected final Log logger = LogFactory.getLog(SocketController.class);
////
////    @MessageMapping("/LIVE_START")
////    @SendToUser("")
////    public void startLive(ChannelLive channelLive, Principal principal) throws Exception {
////        logger.info("request start live");
////        MyUserDTO myUserDTO = (MyUserDTO) principal;
////        ChannelEntity entity = channelRepository.findById(channelLive.getId()).orElseThrow(() -> new ClientException("Not found channel to start"));
////        entity.setLiveCode(UUID.randomUUID().toString());
////        channelLive.setLive(1);
////        entity.setLive(1);
////        channelLive.setTitle(entity.getTitle());
////        channelLive.setSellerId(myUserDTO.getId());
////        channelLive.setName(entity.getName());
////        channelLive.setAvatar(entity.getAvatar());
////        channelLive.setLiveCode(entity.getLiveCode());
////        channelLiveRedisRepository.save(channelLive);
////        channelRepository.save(entity);
////
////        GenericMessage<byte[]> genericMessage = MessageBuilder.getBuilder().set("channel", channelLive).build();
////        template.send("/topic/LIVE_START/" + channelLive.getId(), genericMessage);
////        logger.info("start live successs");
////    }
//
//
////    @MessageMapping("/LIVE_SNAP_BACKGROUND")
////    public void snapshotBackground(ChannelLive channelLive, Principal principal) throws Exception {
////        MyUserDTO myUserDTO = (MyUserDTO) principal;
////        logger.info(String.format("Seller id %d request snap background", myUserDTO.getId()));
//////        snapshot.save(channelLive.getId().intValue(),channelLive.getLiveCode());
////        logger.info(String.format("Seller id %d snap background success", myUserDTO.getId()));
////    }
//
//    @MessageMapping("/EVENT_START")
//    public void startEvent(ChannelLive channel, Principal principal) throws Exception {
//        MyUserDTO myUserDTO = (MyUserDTO) principal;
//        logger.info(String.format("Seller id %d request start event", myUserDTO.getId()));
//        channel = channelLiveRedisRepository.findById(channel.getId()).orElseThrow(() -> new ClientException("Not Found Channel"));
//        Onsale onsaleRedis = onsaleRedisRepository.findBySellerId((myUserDTO.getId())).orElseThrow(() -> new ClientException("Not Found Event"));
//        OnsaleDTO onsale = onsaleRedis.getEntity();
//        channel.setOnsale(onsale);
//
//        long time = onsale.getCreatedDate().getTime() + onsale.getTime() * 1000 - System.currentTimeMillis();
//        if (time <= 0) return;
//        channelLiveRedisRepository.save(channel);
//        GenericMessage<byte[]> genericMessage = MessageBuilder.getBuilder()
////                .set("topic", payload.getTopic())
//                .set("event", onsale)
//                .set("timeLeft", time / 1000)
//                .build();
//        template.send(String.format("/topic/EVENT_START/%d", channel.getId()), genericMessage);
//        logger.info(String.format("Seller id %d start event success", myUserDTO.getId()));
//    }
//
//
//    @MessageMapping("/EVENT_END")
//    public void endEvent(ChannelLive channel, Principal principal) throws Exception {
//        MyUserDTO myUserDTO = (MyUserDTO) principal;
//        logger.info(String.format("Seller id %d request end event", myUserDTO.getId()));
//        channel = channelLiveRedisRepository.findById(channel.getId()).orElseThrow(() -> new ClientException("Not Found Channel"));
//        Onsale onsaleRedis = onsaleRedisRepository.findBySellerId((myUserDTO.getId())).orElseThrow(() -> new ClientException("Not Found Event"));
//        OnsaleDTO onsale = onsaleRedis.getEntity();
//        channel.setOnsale(onsale);
//
//        if (channel.getOnsale().getType() == 0) {
//            List<Bid> bids = channel.getBids();
//            Bid bid = (bids == null || bids.size() == 0) ? null : bids.get(bids.size() - 1);
//            Optional<User> userEntityOptional = userRedisRepository.findById(bid.getUserId());
//            GenericMessage<byte[]> genericMessage = MessageBuilder.getBuilder()
//                    .set("topic", "EVENT_BID_WIN")
//                    .set("buyer_win", userEntityOptional.isPresent() ?
//                            userEntityOptional.get().getUser().getUsername() : null)
//                    .set("isExistsBuyerWin", userEntityOptional.isPresent())
//                    .build();
//            template.send(String.format("/topic/EVENT_BID_WIN/%d", channel.getId()), genericMessage);
//        }
//        channel.setOnsale(null);
//        channel.setBids(new ArrayList<>());
//        channelLiveRedisRepository.save(channel);
//        GenericMessage<byte[]> genericMessage = MessageBuilder.getBuilder().build();
//        template.send(String.format("/topic/EVENT_END/%d", channel.getId()), genericMessage);
//        logger.info(String.format("Seller id %d end event success", myUserDTO.getId()));
//    }
//
//
//    @MessageMapping("/EVENT_BID_PLUS_TIME")
//    public void eventBidPlusTime(ChannelLive channel, Principal principal) throws Exception {
//        MyUserDTO myUserDTO = (MyUserDTO) principal;
//        logger.info(String.format("Seller id %d request plus time for auction event", myUserDTO.getId()));
//
//        channel = channelLiveRedisRepository.findById(channel.getId()).orElseThrow((() -> new ClientException("Not Found Channel")));
//
//        if (channel.getOnsale() == null
//                || channel.getOnsale().getType() != 0) return;
//        OnsaleDTO onsale = channel.getOnsale();
//
//        long time = onsale.getCreatedDate().getTime() + onsale.getTime() * 1000 - System.currentTimeMillis();
//        if (time < 0) return;
//        onsale.setTime(onsale.getTime() + 15);
//
//        GenericMessage<byte[]> genericMessage = MessageBuilder.getBuilder()
//                .set("timeLeft", time / 1000 + 15)
//                .build();
//        template.send(String.format("/topic/EVENT_BID_PLUS_TIME/%d", channel.getId()), genericMessage);
//        logger.info(String.format("Seller id %d plus time for auction event success", myUserDTO.getId()));
//    }
//
//
//    @MessageMapping("/EVENT_BID_RESULT")
//    public void eventBidResult(ChannelLive channel, Principal principal) throws Exception {
//        MyUserDTO myUserDTO = (MyUserDTO) principal;
//        logger.info(String.format("Seller id %d request get auction result", myUserDTO.getId()));
//        channel = channelLiveRedisRepository.findById(channel.getId()).orElseThrow((() -> new ClientException("Not Found Channel")));
//        OnsaleDTO onsale = channel.getOnsale();
//        if (onsale == null || onsale.getType() != 0) return;
//        long time = onsale.getCreatedDate().getTime() + onsale.getTime() * 1000 - System.currentTimeMillis();
//        if (time > 0) return;
//        List<Bid> bids = channel.getBids();
//        Bid bid = bids.size() > 0 ? bids.get(bids.size() - 1) : null;
//
//        MyUserDTO userWin = null;
//        if (bid != null && bid.getUserId() != null) {
//            Optional<User> userEntityOptional = userRedisRepository.findById(bid.getUserId());
//            if (userEntityOptional.isPresent()) {
//                userWin = userEntityOptional.get().getUser();
//            }
//        }
//
//        GenericMessage<byte[]> genericMessage = MessageBuilder.getBuilder()
//                .set("buyer_win", userWin != null ?
//                        userWin.getUsername() : null)
//                .set("isExistsBuyerWin", userWin != null)
//                .set("timeLeft", time / 1000)
//                .set("timeEnd", time <= 0)
//                .build();
//        template.send(String.format("/topic/EVENT_BID_RESULT/%d", channel.getId()), genericMessage);
//        logger.info(String.format("Seller id %d get auction result success", myUserDTO.getId()));
//    }
//
//
//    @MessageMapping("/CHANNEL_JOIN")
//    public void channelJoin(ChannelLive channel, Principal principal) throws Exception {
//        MyUserDTO myUserDTO = (MyUserDTO) principal;
//        logger.info(String.format("User id %d request join channel %d", myUserDTO.getId(), channel.getId()));
//        channel = channelLiveRedisRepository.findById(channel.getId()).orElse(new ChannelLive());
//        if (channel.getId() == null) {
//            ChannelEntity entity = channelRepository.findById(channel.getId()).get();
//            channel = Converter.toModel(entity, ChannelLive.class);
//        }
//        channel.getSubscribers().add(myUserDTO.getId());
////        ExecutorService executor = Executors.newSingleThreadExecutor();
////        executor.execute(()-> );
//
//        //        channel = channelLiveRedisRepository.findById(channel.getId()).orElseThrow(() -> new ClientException("Not Found Channel"));
////        if (channel.getLive() == 0) throw new ClientException("CHANNEL not start");
//        GenericMessage<byte[]> genericMessage = MessageBuilder.getBuilder()
//                .set("views", channel.getSubscribers().size())
//                .set("user", myUserDTO.getUsername()).build();
//        template.send(String.format("/topic/CHANNEL_JOIN/%d", channel.getId()), genericMessage);
//
//        GenericMessage<byte[]> genericMessage2 = MessageBuilder.getBuilder()
//                .set("messages", channel.getMessages())
//                .build();
//        template.send(String.format("/topic/CHANNEL_ALL_MESSAGE/%d", channel.getId()), genericMessage2);
//
//        Message msg = new Message();
//        msg.setFrom(myUserDTO.getUsername());
//        msg.setMessage("has joined LIVE");
//
//        GenericMessage<byte[]> genericMessage3 = MessageBuilder.getBuilder()
//                .set("message", msg)
//                .build();
//        template.send(String.format("/topic/CHANNEL_MESSAGE/%d", channel.getId()), genericMessage3);
//
//
////        if (channel.getSellerId() == null || !channel.getSellerId().equals(myUserDTO.getId()))
////        sendMessageToRoom(channel.getSubscribers(), message);
//
//        if (channel.getOnsale() != null) {
//            OnsaleDTO onsale = channel.getOnsale();
//            long time = onsale.getCreatedDate().getTime() + onsale.getTime() * 1000 - System.currentTimeMillis();
//            if (time <= 0) return;
//
//            GenericMessage<byte[]> genericMessage4 = MessageBuilder.getBuilder()
//                    .set("event", onsale)
//                    .set("timeLeft", time / 1000).build();
//            template.send(String.format("/topic/EVENT_START/%d", channel.getId()), genericMessage4);
//        }
//        ChannelView channelView = channelViewRedisRepository.findById(channel.getId()).orElse(new ChannelView());
//        if (channelView.getId() == null) {
//            channelView.setId(channel.getId());
//        }
////        userRedisRepository.save(sessionUser);
//        channelView.setViews(channel.getSubscribers().size());
//        channelLiveRedisRepository.save(channel);
//        channelViewRedisRepository.save(channelView);
//        logger.info(String.format("User id %d join channel %d success", myUserDTO.getId(), channel.getId()));
//    }
//
//
//    @MessageMapping("/CHANNEL_MESSAGE")
//    public void channelMessage(Message msg, Principal principal) throws Exception {
//        MyUserDTO myUserDTO = (MyUserDTO) principal;
//        logger.info(String.format("User id %d request send message", myUserDTO.getId()));
//        ChannelLive channel = channelLiveRedisRepository.findById(msg.getTo()).orElse(new ChannelLive());
//        if (channel.getId() == null) {
//            ChannelEntity entity = channelRepository.findById(msg.getTo()).get();
//            channel = Converter.toModel(entity, ChannelLive.class);
//        }
//        msg.setFrom(myUserDTO.getUsername());
//        channel.getMessages().add(msg);
//        channelLiveRedisRepository.save(channel);
//        GenericMessage<byte[]> genericMessage = MessageBuilder.getBuilder()
//                .set("message", msg).build();
//
//        template.send(String.format("/topic/CHANNEL_MESSAGE/%d", channel.getId()), genericMessage);
//        logger.info(String.format("User id %d send message success", myUserDTO.getId()));
//    }
//
//    @MessageMapping("/CHANNEL_EXIT")
//    public void channelExit(ChannelLive channelLive, Principal principal) throws Exception {
//        MyUserDTO myUserDTO = (MyUserDTO) principal;
//        logger.info(String.format("User id %d request exit channel", myUserDTO.getId()));
//        ChannelLive channel = channelLiveRedisRepository.findById(channelLive.getId()).orElse(new ChannelLive());
//        if (channel.getId() == null) {
//            ChannelEntity entity = channelRepository.findById(channelLive.getId()).get();
//            channel = Converter.toModel(entity, ChannelLive.class);
//        }
//        channel.getSubscribers().removeIf(e -> e.equals(myUserDTO.getId()));
//        channelLiveRedisRepository.save(channel);
//        GenericMessage<byte[]> genericMessage = MessageBuilder.getBuilder()
//                .set("views", channel.getSubscribers().size())
//                .build();
//        template.send(String.format("/topic/CHANNEL_EXIT/%d", channel.getId()), genericMessage);
//        logger.info(String.format("User id %d exit channel success", myUserDTO.getId()));
//    }
//
//    @MessageMapping("/CHANNEL_HEART")
//    public void channelHeart(Message message, Principal principal) throws Exception {
//        MyUserDTO myUserDTO = (MyUserDTO) principal;
//        logger.info(String.format("User id %d request send heart channel", myUserDTO.getId()));
//        GenericMessage<byte[]> genericMessage = MessageBuilder.getBuilder().build();
//        template.send(String.format("/topic/CHANNEL_HEART/%d", message.getTo()), genericMessage);
//        logger.info(String.format("User id %d send heart to channel success", myUserDTO.getId()));
//    }
//
//    @MessageMapping("/CHANNEL_ALL_BIDS")
//    public void channelAllBids(ChannelLive channelLive, Principal principal) throws Exception {
//        MyUserDTO myUserDTO = (MyUserDTO) principal;
//        logger.info(String.format("User id %d request get all bids", myUserDTO.getId()));
//        ChannelLive channel = channelLiveRedisRepository.findById(channelLive.getId()).orElse(new ChannelLive());
//        GenericMessage<byte[]> genericMessage = MessageBuilder.getBuilder()
//                .set("bids", channel.getBids())
//                .build();
//        template.send(String.format("/topic/CHANNEL_ALL_BIDS/%d", channelLive.getId()), genericMessage);
//        logger.info(String.format("User id %d get all bids success", myUserDTO.getId()));
//    }
//}
//
