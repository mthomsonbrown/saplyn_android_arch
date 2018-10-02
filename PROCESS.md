9/23/2018:
Well, coming back to this after a good year away.  I just joined (trial period-ed) a gym, so I want 
to add weight machine sets/reps as a trackable thing.  The things I need to do for that:

- Need to support different types of entries.
    - Instead of an "add clicks" button, I need an onClickListener registered with the recyclerView
      in HomeActivityFragment.
          - This way, I can have different lists of clicks.
- EntrySets should support types.
    - Smoking entries are one click, whereas weight lifting should take an integer for the number 
    of reps.
- EntrySets should have taggable attributes, such as exercise:strength:biceps.
    - With this, you would be able to graph various data sets: how much pec lifts over time, how 
    many days on legs, exercise total, etc.
- Cardio should be considered as well, but I'm more interested in lifting numbers at this point.  
I'd like to rely on fitbit for cardio if I can.

NOTE: Hopefully the architecture of this thing is close enough to best practices that I can
understand it after I've got my head back in android, but looking at it now, it would be nice if the
classes/methods said what their purposes were in the system, maybe even a diagram...

9/25/2018:
Well, I got the recycler view to respond to click events in the home activity, but it's only 
recognized as a recycler view, rather than an individual item.  Leaving it for now, but my current 
thought is that if all the items in the RecyclerView extended View, I could possibly attach the 
onClickListener to them individually, which should give me the right ID. 

9/26/2018:
Well, I can get the name of the view that was hit now.  I put the onClickListener inside the 
EntrySetAdapter.  I'm not sure if that's necessary, and I'm not sure how I feel about it, but it's 
a good step forward.  The next thing to do would be to spawn a generic entry set detail event, 
passing in the entry set object...and also make the views inside the recycler view bigger.  They 
are a pain to click right now...

Well, I got the entryset items to appear bigger...

It looks like the current clicks view is accessing a table called clicks, so it won't be very
straightforward to create individual click lists.  I need to edit the DB schema.  My thought now is
to have the entryset table item's id be a foreign key in the clicks table, and then the clicks
table will need to expand to contain columns for more sophisticated clicks...ugh

9/28/2018:
Well, I made a lot of progress, and also left the app completely broken...The shit in the repo
should work just fine, but locally I tried to pull the EntrySet name from the DB and got
pretty lost...also drunk so valid excuse