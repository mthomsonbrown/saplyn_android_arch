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
